--AUTHORITIES BEGIN--
INSERT INTO authority values (1, 'ROLE_REG_CUSTOMER');
INSERT INTO authority values (2, 'ROLE_COMPANY_OWNER');
INSERT INTO authority values (3, 'ROLE_ADMIN');
INSERT INTO authority values (4, 'ROLE_COMPANY');
--AUTHORITIES END--
INSERT INTO public.verification_code(
    id, date_of_creation, code) VALUES
    (0, null, '');
INSERT INTO public.users(
	id, api_token, email, first_name, is_activated, is_using2fa, last_name, password, secret, verification_code)
	VALUES (100, 'asdas', 'admin@admin.com', 'admin', true, false,  'admin', '$2a$10$ri5e5kZyPfMHH8qfKISyOuhSdttNOuT2MQZu916g3KriYuz0JYuaq', 'HSHAH', 0);
INSERT INTO user_authority VALUES (100, 3);