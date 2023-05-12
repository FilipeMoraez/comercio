import { useEffect, useState } from 'react'

function Home({ auth }) {

    

    function createPostOperation(funcao, data) {
        fetch("saldo/api/" + funcao, {
            method: "POST",
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json',
                'Authorization': auth
            }
        })
            .then((resp) => resp.json())
            .then((resp) => {
                setModal("modal")
                setState(resp);
            })
            .catch(err => console.log(err))
    }

    const [saldo, setSaldo] = useState([])
    const [movimentacoes, setMovimentacoes] = useState([])
    const [movDiarias, setMovDiarias] = useState([])
    const [principal, setPrincipal] = useState("home")
    const [modal, setModal] = useState("")
    const [debito, setDebito] = useState({})
    const [credito, setCredito] = useState({})
    const [state, setState] = useState({})
    const [tipoExtrato, setTipoExtrato] = useState("normal")

    function handleChangeDebito(e) {
        setDebito({ ...debito, [e.target.name]: e.target.value })
    }

    function handleChangeCredito(e) {
        setCredito({ ...credito, [e.target.name]: e.target.value })
    }

    function real(text) {
        if (text != null) {
            if (String(text).indexOf(".") > 0) {
                return "R$ " + text + "0";
            } else return "R$ " + text + ".00";
        } else return "RS 00.00";
    }

    function definePrincipal(text) {
        setPrincipal(text);
    }

    const debitar = (e) => {
        e.preventDefault();
        createPostOperation("debit", debito)
    }
    const creditar = (e) => {
        e.preventDefault();
        createPostOperation("credit", credito)
    }

    function movimentacoesDiarias() {
        fetch("saldo/api/balance/ofday", {
            method: "GET",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': auth
            }
        })
            .then((resp) => resp.json())
            .then((data) => {
                setMovDiarias(data)
                setTipoExtrato("diario")
            })
            .catch(err => console.log(err))

    }

    useEffect(() => {
        fetch("saldo/api/balance", {
            method: "GET",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': auth
            }
        })
            .then((resp) => resp.json())
            .then((data) => {
                setSaldo(data)
            })
            .catch(err => console.log(err))


        fetch("saldo/api/balance/all", {
            method: "GET",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': auth
            }
        })
            .then((resp) => resp.json())
            .then((data) => {
                setMovimentacoes(data)
            })
            .catch(err => console.log(err))

    }, [state])

    return (
        <div class="home">
            <           div class="menu">
                <div class="top">
                    <h1>Carteira Comercio</h1>
                </div>
                <div class="list">
                    <div onClick={() => definePrincipal("home")}>Home</div>
                    <div onClick={() => definePrincipal("debito")}>Débito</div>
                    <div onClick={() => definePrincipal("credito")}>Crédito</div>
                </div>
            </div>
            <div class="saldo">
                <h3>Saldo: {real(saldo.saldo)}</h3>
            </div>
            {principal == "home" && (
                <div>
                    <div class="movimentacoes">
                        <div class="header">
                            <h2 onClick={() => setTipoExtrato("normal")}>Extrato</h2>
                            <h2 onClick={() => movimentacoesDiarias()}>Extrato do dia</h2></div>

                        {tipoExtrato == "normal" && (
                            <div>
                                <table class="mov">
                                    <thead>
                                        <tr>
                                            <th>Id Transação</th>
                                            <th>Descrição</th>
                                            <th>Tipo Transação</th>
                                            <th>Valor</th>
                                            <th>Data</th>
                                            <th>Saldo</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {movimentacoes.length > 0 &&
                                            movimentacoes.map((mov) => (
                                                <tr>
                                                    <td>{mov.idTransacao}</td>
                                                    <td>{mov.descricao}</td>
                                                    <td>{mov.tipo}</td>
                                                    <td>{mov.valor}</td>
                                                    <td>{mov.data}</td>
                                                    <td>{mov.saldo}</td>
                                                </tr>
                                            ))
                                        }
                                    </tbody>
                                </table>
                            </div>
                        )}

                        {tipoExtrato == "diario" && (
                            <div>
                                <table class="mov">
                                    <thead>
                                        <tr><th>Id Transação</th><th>Descrição</th><th>Tipo Transação</th><th>Valor</th><th>Data</th><th>Saldo</th></tr>
                                    </thead>
                                    <tbody>
                                        {movDiarias.length > 0 &&
                                            movDiarias.map((m) => (
                                                <tr>
                                                    <td>{m.idTransacao}</td>
                                                    <td>{m.descricao}</td>
                                                    <td>{m.tipo}</td>
                                                    <td>{m.valor}</td>
                                                    <td>{m.data}</td>
                                                    <td>{m.saldo}</td>
                                                </tr>
                                            ))
                                        }
                                    </tbody>
                                </table>
                            </div>
                        )}


                    </div>
                </div>
            )}

            {principal == "debito" && (
                <div class="movimento">
                    <h1>Deseja realizar um debito?</h1>

                    <form onSubmit={debitar}>
                        <div>
                            <label>Qual é a descrição pra gente identificar essa transação?</label>
                        </div>
                        <div>
                            <input type="text" onChange={handleChangeDebito} required name="descricao" />
                        </div>
                        <div>
                            <label>Qual é o valor da transação?</label>
                        </div>
                        <div>
                            <input type="number" onChange={handleChangeDebito} required name="valor" />
                        </div>
                        <div>
                            <input type="submit" class="dbt" value="Debitar" />
                        </div>
                    </form>
                </div>
            )}


            {principal == "credito" && (
                <div class="movimento">
                    <h1>Deseja realizar um crédito?</h1>

                    <form onSubmit={creditar}>
                        <div>
                            <label>Qual é a descrição pra gente identificar essa transação?</label>
                        </div>
                        <div>
                            <input type="text" onChange={handleChangeCredito} required name="descricao" />
                        </div>
                        <div>
                            <label>Qual é o valor da transação?</label>
                        </div>
                        <div>
                            <input type="number" onChange={handleChangeCredito} required name="valor" />
                        </div>
                        <div>
                            <input type="submit" value="Creditar" />
                        </div>
                    </form>
                </div>
            )}


            {modal == "modal" && (
                <div class="layer">
                    <div class="block">
                        <h1>Salvo com sucesso!</h1>
                        <button onClick={() => setModal("")}>Fechar</button>
                    </div>
                </div>
            )}
        </div>

    )
}

export default Home