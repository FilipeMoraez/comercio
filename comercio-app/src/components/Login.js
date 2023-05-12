import { useState } from "react"

function Login({handleSubmit, loginData}){

    const[login, setLogin] = useState(loginData || {})
    
    
    const submit = (e) => { 
        e.preventDefault();
       handleSubmit(login)
    }
    function handleChangeLogin(e){
        setLogin({...login, [e.target.name]: e.target.value})
    }

  return(  <div class="background">
        <div class="form" onSubmit={submit}>
            <form>
                <h1>Login</h1>
                <div>
                    <label>Usuário: </label>
                    <input name="username" id="username" required onChange={handleChangeLogin} type="text" />
                </div>
                <div>
                    <label>Senha: </label>
                    <input name="password" id="password" required onChange={handleChangeLogin} type="password" />
                </div>
                <div>
                    <input type="submit" value="Login" />
                </div>
            </form>
        </div>

    </div>
  )
}
export default Login
