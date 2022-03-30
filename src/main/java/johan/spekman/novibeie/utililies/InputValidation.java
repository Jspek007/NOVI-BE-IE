package johan.spekman.novibeie.utililies;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class InputValidation {
    public StringBuilder validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                stringBuilder.append(fieldError.getField()).append(" ").append(fieldError.getDefaultMessage());
                stringBuilder.append("\n");
            }
            return stringBuilder;
        }
        return null;
    }
}
