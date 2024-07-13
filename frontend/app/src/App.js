import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import MoneyCount from './MoneyCount.js';

function App() {


  return (
    <BrowserRouter>
      <h2 style={{textAlign: 'center'}}>Calculates the minimum number of coins to make up the target amount</h2>
      <Routes>
        <Route path="/"
               element={<MoneyCount/>}/>
      </Routes>
    </BrowserRouter>

  );
}

export default App;
