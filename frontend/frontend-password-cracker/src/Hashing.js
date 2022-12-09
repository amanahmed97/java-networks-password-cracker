import { useState, useEffect } from 'react';
import md5 from 'md5-hash';
// import {workers} from './App';


function generateHash(workers) {
    const password = document.getElementById('password').value;
    if(password){
      console.log(password);
      console.log(md5(password));
      console.log('password');
      console.log(workers);
      
      document.getElementById('passwordDisplay').innerHTML = "Entered Password: "+password;
      document.getElementById('hashDisplay').innerHTML = "MD5 Hash: "+md5(password)
                                                         +"<br> Sending the Hash to Server to crack"
                                                         +"<br> Waiting for Response ....";
      
      console.log(`/crack?hash=${md5(password)}&useworkers=${workers}`);
      // API call
      fetch(`/crack?hash=${md5(password)}&useworkers=${workers}`)
       .then((response) => response.json())
       .then((data) => {
         console.log("data");
         console.log(data);
         console.log(data.password);

         document.getElementById('crackedDisplay').innerHTML = "Cracked Password: "+data.password;

         return JSON.stringify(data.password);         
       })
       .catch((err) => {
          console.log("err.message");
          console.log(err.message);
       });

    }
  }

  function getWorkers() {                  
      
      // API call
      fetch(`/getworkers`)
         .then((response) => response.json())
         .then((data) => {
         console.log("data - numberworkers");
         console.log(data);
         console.log(data.numberworkers);

         document.getElementById('numberWorkers').innerHTML = "Available Workers: "+data.numberworkers;

         return JSON.stringify(data.numberworkers);
         })
         .catch((err) => {
            console.log("err.message");
            console.log(err.message);
         });
      
   }

  export {generateHash,getWorkers};