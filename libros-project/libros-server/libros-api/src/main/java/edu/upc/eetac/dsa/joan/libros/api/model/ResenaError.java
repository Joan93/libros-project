package edu.upc.eetac.dsa.joan.libros.api.model;

public class ResenaError {
	
    private int status;
    private String message;

    public ResenaError() {
            super();
    }

    public ResenaError(int status, String message) {
            super();
            this.status = status;
            this.message = message;
    }

    public int getStatus() {
            return status;
    }

    public void setStatus(int status) {
            this.status = status;
    }

    public String getMessage() {
            return message;
    }

    public void setMessage(String message) {
            this.message = message;
    }
}
