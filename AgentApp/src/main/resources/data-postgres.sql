--AUTHORITIES BEGIN--
INSERT INTO authority values (1, 'ROLE_REG_CUSTOMER');
INSERT INTO authority values (2, 'ROLE_COMPANY_OWNER');
INSERT INTO authority values (3, 'ROLE_ADMIN');
INSERT INTO authority values (4, 'ROLE_COMPANY');
--AUTHORITIES END--
INSERT INTO public.users(id, email, first_name, last_name, password)VALUES (100, 'admin', 'admin', 'admin', '$2a$10$ri5e5kZyPfMHH8qfKISyOuhSdttNOuT2MQZu916g3KriYuz0JYuaq');
INSERT INTO user_authority VALUES (100, 3);