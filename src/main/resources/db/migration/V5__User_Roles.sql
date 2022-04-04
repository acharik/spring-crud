CREATE TABLE user_roles(
    user_id INT,
    role_id INT,
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) references users,
    CONSTRAINT fk_user_roles_roles FOREIGN KEY (role_id) references roles
)