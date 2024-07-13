import './App.css';
import axios from "axios";
import {useState} from "react";

function App() {
  const [checkbox, setCheckbox] = useState({
    0.01: false,
    0.05: false
  });
  const [target, setTarget] = useState('');

  const handleToggle = ({ target }) =>
    setCheckbox(s => ({ ...s, [target.name]: !s[target.name] }));

  const handleTargetChange = (event) => {
    setTarget(event.target.value);
  };

  const MONEY_COUNT_API = "http://localhost:8080/api/count";

  function handleCalculateClick(event) {
    event.preventDefault();

    const selectedCheckboxes = Object.keys(checkbox).filter(key => checkbox[key]);

    const data ={
      targetAmount: parseFloat(target),
      denominationList: selectedCheckboxes
    };

    axios.post(MONEY_COUNT_API, data,)
      .then(response =>{
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
        // Updates the errors state variable to display validation errors
        // if (e.response.status === 400 || e.response.status === 500){
        //   setErrors(e.response.data)
        // }
      })
  }

  return (
    <div className="App">
      <h1>Calculates the minimum number of coins to make up the target amount</h1>
      <form onSubmit={handleCalculateClick}>
        <label htmlFor='target'>Target Amount (0 - 10,000.00): </label><br/>
        <input type='text' name='target' value={target}/><br/>
        <div>
          <label htmlFor='denominations'>Choose coin denominations: </label><br/>
          <div>
            {Object.entries(checkbox).map(([key, value]) => (
              <div key={key}>
                <input
                  type="checkbox"
                  onChange={handleToggle}
                  name={key}
                  checked={value}
                  id={"checkbox-${key}"}
                />
                <label htmlFor={"checkbox-${key}"}>${key}</label>
              </div>
            ))}
          </div>
        </div>

        <button type="submit">Calculate</button>
      </form>
    </div>
  );
}

export default App;
