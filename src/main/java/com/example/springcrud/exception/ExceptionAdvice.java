package com.example.springcrud.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.DateTimeException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionAdvice {
      @ExceptionHandler
        public ResponseEntity<String> handleR2dbcEx (ConstraintViolationException ex){
          if( ex.getConstraintName().contains("UNIQ_NUM_CONSTRAINT")){
              ex.getConstraintName();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Контракт с таким номером уже существует");
            }

            if(ex.getConstraintName().contains("DATE_END >= DATE_BEGIN"))  {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Дата конца должна быть позже даты начала");
            }
            else  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getConstraintName());
        }
    @ExceptionHandler
    public ResponseEntity<String> handleNotFoundContractEx(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
    @ExceptionHandler
    public ResponseEntity<String> handleJsonMappingEx(JsonMappingException ex){
        if (ex.getMessage().contains("Failed to deserialize java.time.LocalDate:")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Неверный формат даты");
        }
          if (ex.getMessage().contains("marked non-null but is null")){
              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Обязательные поля должны быть заполнены");
          }
          else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler
    public ResponseEntity<String> handleJsonParseEx(JsonParseException ex){


            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Проверьте правильность введенных данных" );

    }
    @ExceptionHandler
    public ResponseEntity<String> handleMethodArgumentNotValidEx( MethodArgumentNotValidException ex ) {
          if(ex.getMessage().contains("Неверный формат")) {
              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Неверный формат");
          }
          else  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler
    public ResponseEntity<String> handleDateTimeEx(DateTimeException ex){
          if(ex.getMessage().contains("Дата не входит в диапазон значений")){
              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Дата не входит в диапазон значений");
          }
          else  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
