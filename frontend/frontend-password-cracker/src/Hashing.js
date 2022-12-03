import md5 from 'md5-hash';

function generateHash() {
    const password = document.getElementById('password').value;
    console.log(password);
    console.log(md5(password));
    console.log('password');
    // alert('password');
    document.getElementById('passwordDisplay').innerHTML = "Password: "+password;
    document.getElementById('hashDisplay').innerHTML = "MD5 Hash: "+md5(password);
    // if (password) {
    //   // generateHash(password);      
    //   document.getElementById('password').value = '';
    //   document.getElementById('password').focus();      
    // }
  }


  export default generateHash;