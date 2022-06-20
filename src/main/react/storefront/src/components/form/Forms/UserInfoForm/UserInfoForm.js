import React, { useState } from "react";
import "./UserInfoForm.scss";
import "react-datepicker/dist/react-datepicker.css";
import DatePicker from "react-datepicker";
import nl from "date-fns/locale/nl";
import InputField from "../FormComponents/InputField";

function UserInfoForm() {
  const [startDate, setStartDate] = useState(new Date());

  return (
    <section className="form-container">
      <form>
        <section className="form-50">
          <label htmlFor="firstName">
            Voornaam:
            <InputField
              labelName="firstName"
              inputType="text"
              idValue="firstName"
              placeholder="Voornaam"
            />
          </label>
          <label htmlFor="lastName">
            Achternaam:
            <InputField
              labelName="lastName"
              inputType="text"
              idValue="lastName"
              placeholder="Achternaam"
            />
          </label>
        </section>
        <section className="form-50">
          <label htmlFor="birth-of-date">
            Geboortedatum:
            <DatePicker
              className="form-date-picker"
              selected={startDate}
              onChange={(date) => setStartDate(date)}
              locale={nl}
              dateFormat="dd/MM/yyyy"
              maxDate={startDate}
            />
          </label>
          <label htmlFor="gender">
            Geslacht:
            <section className="gender-picker-container">
              <select name="gender" id="gender" className="gender-select">
                <option value="male">Man</option>
                <option value="female">Vrouw</option>
                <option value="other-gender">Anders</option>
              </select>
            </section>
          </label>
        </section>
        <section className="form-100">
          <label htmlFor="streetInfo">
            Straat en huisnummer:
            <InputField
              labelName="streetInfo"
              inputType="text"
              idValue="streetInfo"
              placeholder="Straatnaam en huisnummer"
            />
          </label>
        </section>
        <section className="form-50">
          <label htmlFor="zipCode">
            Postcode:
            <InputField
              labelName="zipCode"
              inputType="text"
              idValue="zipCode"
              placeholder="Postcode"
            />
          </label>
          <label htmlFor="city">
            Plaatsnaam:
            <InputField
              labelName="city"
              inputType="text"
              idValue="city"
              placeholder="Plaatsnaam"
            />
          </label>
        </section>
      </form>
    </section>
  );
}

export default UserInfoForm;
