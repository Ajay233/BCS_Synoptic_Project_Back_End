--RESTRICTED users

INSERT INTO users (username, password, permission) values ('restricted-user1', '$2y$10$GgL4GkB72oSEX85Cnl6xhOGt43IvUUysZ8a/h2MLnkkVvQ1raZUh2', 'Restricted');
INSERT INTO users (username, password, permission) values ('restricted-user2', '$2y$10$zieskmxtt7S4AgufH.DdqeP4XogaA/SPPfzHK/QHgE7V3KDXpNTTS', 'Restricted');


--VIEW users

INSERT INTO users (username, password, permission) values ('view-user1', '$2y$10$7dyHkKYraVGrmtms8vsL7urO/9VM.MvJsGatqHBcA.FpMlUaObkzq', 'View');
INSERT INTO users (username, password, permission) values ('view-user2', '$2y$10$lgj.2txxDk7a.A7phSAvnOWZrGno8WvxXCAB.h66mvJa0.IhPgjYa', 'View');


--EDIT users

INSERT INTO users (username, password, permission) values ('edit-user1', '$2y$10$LSFmhARaYSHRRVYvviBL1ebKd6d.bjufuVWbHz9yyztJ7sAypzdx.', 'Edit');
INSERT INTO users (username, password, permission) values ('edit-user2', '$2y$10$2Uq5Zaadfruym9mYlJAgU.4YtAVmck1iOTcBbni0oRQwkI5j0bkUG', 'Edit');