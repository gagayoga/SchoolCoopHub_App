package com.schoolcoophub.app.login;

public class LoginResponse {

    private Data data;


    public Data getData() {
        return data;
    }

    public static class Data{


        private int id;

        private String email;

        private String name;

        private Role role;

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


        public Role getRole() {
            return role;
        }

        public void setRole(Role role) {
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

    public class Role{
        private int id;
        private String name_role;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName_role() {
            return name_role;
        }

        public void setName_role(String name_role) {
            this.name_role = name_role;
        }
    }
}
