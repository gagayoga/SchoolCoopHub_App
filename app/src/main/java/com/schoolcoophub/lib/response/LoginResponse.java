package com.schoolcoophub.lib.response;

public class LoginResponse {

    private Data data;

    public Data getData() {
        return data;
    }

    public static class Data{
        private int id;

        private String email;

        private String name;

        private String role;

        private String status;

        private String token;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }


    }

    public void setData(Data data) {
        this.data = data;
    }
}
