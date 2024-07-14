import React, {useEffect, useState} from 'react'
import axios from "axios";
import MoneyCountResult from './MoneyCountResult.js';
import config from "./config";

export default function MoneyCount() {
  const [denominations, setDenominations] = useState([]);
  const [checkbox, setCheckbox] = useState([]);
  const [targetAmount, setTargetAmount] = useState('');
  const [results, setResults] = useState(null);
  const [errors, setErrors] = useState('');

  const handleToggle = ({target}) =>
    setCheckbox(s => ({...s, [target.name]: !s[target.name]}));

  const handleTargetChange = (event) => {
    setTargetAmount(event.target.value);
  };

  const MONEY_COUNT_API = config.MONEY_COUNT_API;

  useEffect(()=>{
    axios.get(MONEY_COUNT_API)
      .then(response=>{
        setDenominations(response.data);
        setCheckbox(response.data.reduce((acc, denom) => ({ ...acc, [denom]: false }), {}))
        console.log(response.data);
      })
      .catch(e=>{
        console.log(e);
      });
  },[])


  function handleCalculateClick(event) {
    event.preventDefault();

    // Clear results first
    setResults(null);
    setErrors('');

    const selectedDenominations = denominations.filter(denom => checkbox[denom]);

    const data = {
      targetAmount: targetAmount,
      denominationSelected: denominations.map(denom => checkbox[denom]),
      denominationAmountList: denominations
    };

    axios.post(MONEY_COUNT_API, data,)
      .then(response => {
        console.log(response.data);
        setResults({
          counts: response.data,
          denominations: selectedDenominations
        });
      })
      .catch(e => {
        console.log(e);
        // Updates the errors state variable to display validation errors
        if (e.response.status === 400 || e.response.status === 500){
          setErrors(e.response.data)
        }
      })
  }

  function getResult(){
    if (results != null){
      return (
        <MoneyCountResult results={results.counts} selectedDenominations={results.denominations}/>
      )
    }
  }

  return (
    <div className="App">

      <form onSubmit={handleCalculateClick}>
        <label htmlFor='target'>Target Amount (0 - 10,000.00): </label><br/>
        <input
          type='text'
          name='target'
          value={targetAmount}
          onChange={handleTargetChange}
        /><br/>
        <div>
          <label htmlFor='denominations'>Choose coin denominations: </label><br/>
          <div>
            {denominations.map((denom) => (
              <div key={denom}>
                <input
                  type="checkbox"
                  onChange={handleToggle}
                  name={denom.toString()}
                  checked={checkbox[denom]}
                  id={'checkbox-${denom}'}
                />
                <label htmlFor={'checkbox-${denom}'}>${denom.toFixed(2)}</label>
              </div>
            ))}
          </div>
        </div>

        <button type="submit">Calculate</button>
      </form>
      <div>
        {errors.targetAmount && <p>{errors.targetAmount}</p>}
        {errors.denominationSelected && <p>{errors.denominationSelected}</p>}
      </div>
      <div>
        {getResult()}
      </div>
    </div>


  )

}