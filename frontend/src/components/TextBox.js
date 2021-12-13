import React from "react";

const TextBox = (props) => {
    
    return (
        <div>
            <label
                for={props.text}
            >
                {props.text}:
            </label>
            <input
                id={props.text}
                name={props.text}
                type={props.type}
                placeholder={props.placeholder}
                onChange={props.change}
            >
            </input>
        </div>
    );
};

export default TextBox;