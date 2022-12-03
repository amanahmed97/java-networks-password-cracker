import './App.css';
import generateHash from './Hashing.js';

function handleGenerate() {
  generateHash();
  console.log('handle');
}

function App() {  
  console.log('app created');
  return (
    <div className="App">
      {/* <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <h1>Password Cracker</h1>        
      </header> */}
      <div className="App-content">
        <h1>Password Cracker</h1>
          <div>
            <p>Enter Password to generate the hash</p>
            <input
              id="password"
              type="password"
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
          </div>
      </div>
      <footer className="App-footer">
        <p>Built by Aman, Rhythm and Nirbhay</p>
      </footer>
    </div>
  );  
}

export default App;
