import './App.css';
import {generateHash, getWorkers} from './Hashing.js';
import { useState } from 'react';


function App() {  
  console.log('app created');

  let [workers, setWorkers] = useState("1")
  
  let handleWorkerChange = (e) => {
    setWorkers(e.target.value)
    console.log("dropdown changed");
    console.log(e.target.value);
    console.log(workers);
  }

  function handleGenerate() {
    generateHash(workers);
    console.log('cracked');
    // console.log(cracked);
    console.log('handle');
  }

  return (
    <div className="App">
      {/* <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <h1>Password Cracker</h1>        
      </header> */}
      <div className="App-content">
        <h1>Password Cracker</h1>
          <div>
            {getWorkers()}            
            <p id="numberWorkers"></p>
            <p>Select how many workers to use</p>
            <select value={workers} onChange={handleWorkerChange}>
              <option num="1">1</option>
              <option num="2">2</option>
              <option num="3">3</option>
              <option num="4">4</option>
            </select>
            <p>Enter 5 character Password to generate the hash</p>
            <input
              id="password"
              type="password"
              maxLength={5}
              minLength={5}
              className="App-input">                
            </input>
            <br></br>
            <button
                className="App-btn"
                onClick={handleGenerate}>
              Generate
            </button>
            <p id="passwordDisplay"></p>
            <p id="hashDisplay"></p>
            <p id="crackedDisplay"></p>
          </div>
      </div>
      <footer className="App-footer">
        <p>Built by Aman, Rhythm and Nirbhay</p>
      </footer>
    </div>
  );  
}

export default App;
