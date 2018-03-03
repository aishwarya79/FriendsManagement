DROP TABLE IF EXISTS spgroup.FRIENDSHIP;
DROP TABLE IF EXISTS spgroup.SUBSCRIBERS;
DROP TABLE IF EXISTS spgroup.BLOCKED_USERS;
DROP TABLE IF EXISTS spgroup.USERS;

-- ###################
-- USER
-- ###################
CREATE TABLE spgroup.USERS
(
	id serial primary key,
	email_addr character varying(50) NOT NULL UNIQUE
);


-- ###################
-- FRIENDSHIP
-- ###################
CREATE TABLE spgroup.FRIENDSHIP
(
	user_id integer,
	friend_id integer,
	constraint fk_user_friendship_user_id foreign key(user_id) references spgroup.USERS(id) ON DELETE CASCADE,
    constraint fk_user_friendship_friend_id foreign key(friend_id) references spgroup.USERS(id) ON DELETE CASCADE
);


-- ###################
-- SUBSCRIPTION
-- ###################
CREATE TABLE spgroup.SUBSCRIBERS
(
	user_id integer,
	subscriber_id integer,
	constraint fk_user_friendship_user_id foreign key(user_id) references spgroup.USERS(id) ON DELETE CASCADE,
    constraint fk_user_friendship_subscriber_id foreign key(subscriber_id) references spgroup.USERS(id) ON DELETE CASCADE
);


-- ###################
-- BLOCKED_USERS
-- ###################
CREATE TABLE spgroup.BLOCKED_USERS
(
	user_id integer,
	blocked_id integer,
	constraint fk_user_friendship_user_id foreign key(user_id) references spgroup.USERS(id) ON DELETE CASCADE,
    constraint fk_user_friendship_blocked_id foreign key(blocked_id) references spgroup.USERS(id) ON DELETE CASCADE
);


