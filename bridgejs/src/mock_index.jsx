import React from "react";
import ReactDOM from "react-dom";

class Button extends React.PureComponent {

    render() {
        return (
            <button>
                Click
            </button>
        );
    }
}

ReactDOM.render(<Button />, document.getElementById("root"));
