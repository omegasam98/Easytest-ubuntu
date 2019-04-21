package e.rkkee.easytest;

import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class User {

        public  static String name;
        public  static String email;
        public  static String contact;
        public  static String uid;

        // Default constructor required for calls to
        // DataSnapshot.getValue(User.class)
        public User() {
        }

        public String retuid() {
            return uid;
        }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public User(String name, String email, String contact) {
            this.name = name;
            this.email = email;
            this.contact= contact;


        }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}

