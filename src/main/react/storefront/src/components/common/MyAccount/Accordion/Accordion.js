import React, { useState } from "react";
import "./Accordion.scss";

const Accordion = ({ title, content }) => {
    
    const [isActive, setIsActive] = useState(false);
    
    return (
        <section className="accordion-item">
            <section className="accordion-title"
                    onClick={() => setIsActive(!isActive)}>
                        <section>{title}</section>
                        <section className="accordion-icon">{isActive ? '-' : '+'}</section>
                    </section>
                    {isActive && <section className="accordion-content">{content}</section>}
        </section>
        
        
    )
}

export default Accordion;