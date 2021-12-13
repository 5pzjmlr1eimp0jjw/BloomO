import React from 'react'
import ReactFlexyTable from "react-flexy-table"
import "react-flexy-table/dist/index.css"
 
const Table = (props) => {
  const data = [
    { label: "Zone Number", value: props.zone},
    { label: "First Frost Date", value: props.firstFrostDate },
    { label: "Last Frost Date", value: props.lastFrostDate },
    { label: "Growing Days", value: props.growingDays },
    { label: "Lowest Temperature Range", value: props.tempR },
    { label: "List Of States sharing the zone", value: props.listOfStates },
  ];
  return (    
    <div>
      <ReactFlexyTable data={data} />
    </div>
  );
}
 
export default Table;