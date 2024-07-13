import React from 'react'

export default function MoneyCountResult({results, selectedDenominations}) {
  return (
    <div>
      <h3>Calculation Results</h3>
        {results.map((result, index) => (
          <p key={index}>${selectedDenominations[index]}: {result} coins</p>
        ))}
    </div>
  )
}