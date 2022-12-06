import { useState, useEffect } from 'react';
import md5 from 'md5-hash';

// const 

function generateHash() {
    const password = document.getElementById('password').value;
    if(password){
      console.log(password);
      console.log(md5(password));
      console.log('password');
      // alert('password');
      document.getElementById('passwordDisplay').innerHTML = "Password: "+password;
      document.getElementById('hashDisplay').innerHTML = "MD5 Hash: "+md5(password);
      
      // API call
      fetch(`/crack?hash=${md5(password)}`)
       .then((response) => response.json())
       .then((data) => {
        console.log("data");
          console.log(data);
       })
       .catch((err) => {
          console.log("err.message");
          console.log(err.message);
       });

    }
  }


  export default generateHash;


  // useEffect(() => {
  //   fetch('/greeting')
  //      .then((response) => response.json())
  //      .then((data) => {
  //       console.log("data");
  //         console.log(data);
  //      })
  //      .catch((err) => {
  //         console.log("err.message");
  //         console.log(err.message);
  //      });
  // }, []);