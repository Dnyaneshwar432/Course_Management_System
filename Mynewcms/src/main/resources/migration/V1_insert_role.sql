-- Insert specific roles into the roles table

INSERT INTO roles (role_id, role_name) VALUES
(${role_id_admin}, '${role_name_admin}'),
(${role_id_user}, '${role_name_user}'),
(${role_id_moderator}, '${role_name_moderator}');
