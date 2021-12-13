import React from 'react'
import ReactFlexyTable from "react-flexy-table"
import "react-flexy-table/dist/index.css"
 
const Table = (props) => {
    const data = [
    {label: "Plant ID", value: props.PlantID, Explanation:""},
    { label: "Plant Common Name", value: props.CommonName},
    { label: "Habitats", value: props.Habit},
    { label: "Height", value: props.Height},
    { label: "Hardiness", value: props.Hardiness},
    { label: "Growth", value: props.Growth, Explanation: "S = slow; M = medium; F = fast"},
    { label: "Moisture", value: props.Moisture, Explanation: "D = dry; M = Moist; We = wet; Wa = water"},
    { label: "Soil", value: props.Soil, Explanation: "S = sandy; M = medium; F = clay"},
    { label: "Shade", value: props.Shade, Explanation: "F = full shade; S = semi-shade; N = no shade"},
    { label: "Edible", value: props.Edible},
    { label: "Medical", value: props.Medical},
    { label: "Other", value: props.Other},
  ];
  return (    
    <div>
      <ReactFlexyTable data={data} pageSize={15}/>   
    </div>
  );
}
 
export default Table;