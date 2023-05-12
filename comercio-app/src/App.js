import logo from './logo.svg';
import './App.css';
import Home from './components/Home'
import Login from './components/Login'
import { useState } from 'react';

function App() {

  const [authorization, setAuthorization] = useState(0)

  function createPostLogin(login){
    login.any = 0
    login.services = []

    fetch("admin/api/login", {
      method:"POST",
      body: JSON.stringify(login),
      headers:{
          'Content-Type':'application/json'
      }
      })
      .then((resp) => resp.text())
      .then((resp) => {
        setAuthorization(String(resp))
        console.log("AQUI = >" + authorization)
      })
      .catch(err => console.log(err))
  
  }


   function authorizado(){
      if(String(authorization).indexOf(".")>1){
        return (<Home auth={authorization} />)
      }else{
        return (<Login handleSubmit={createPostLogin} />)
      }
   }

   

  return (
    <div className="App">
      {authorizado()}
    </div>
  );
}

export default App;
