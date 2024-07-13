import React, {useState} from 'react'
import axios from "axios";
import MoneyCountResult from './MoneyCountResult.js';

const DENOMINATIONS = [0.01, 0.05, 0.1, 0.2, 0.5, 1, 2, 5, 10, 50, 100, 1000];

export default function MoneyCount() {
  const [checkbox, setCheckbox] = useState(
    DENOMINATIONS.reduce((acc, denom) => ({ ...acc, [denom]: false }), {})
  );
  const [targetAmount, setTargetAmount] = useState('');
  const [results, setResults] = useState(null);
  const [errors, setErrors] = useState('');

  const handleToggle = ({target}) =>
    setCheckbox(s => ({...s, [target.name]: !s[target.name]}));

  const handleTargetChange = (event) => {
    setTargetAmount(event.target.value);
  };

  const MONEY_COUNT_API = "http://129.150.57.2/:8080/api/count";

  function handleCalculateClick(event) {
    event.preventDefault();
    const selectedDenominations = DENOMINATIONS.filter(denom => checkbox[denom]);

    const data = {
      targetAmount: targetAmount,
      denominationSelected: DENOMINATIONS.map(denom => checkbox[denom]),
      denominationAmountList: DENOMINATIONS
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
            {DENOMINATIONS.map((denom) => (
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