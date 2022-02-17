CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE image (
    id uuid DEFAULT uuid_generate_v4(),
    category VARCHAR NOT NULL,
    content BYTEA NOT NULL,
    extension VARCHAR NOT NULL,
    PRIMARY KEY (id)
);
GRANT ALL PRIVILEGES ON TABLE image TO myuser;
    --Some dummy data
INSERT INTO image(category,content,extension) VALUES('cat','fwpkfwefkwefowekfofkqepőf','jpg');
INSERT INTO image(category,content,extension) VALUES('dog','3red2d35g56h43fd3d3rvt4','jpg');
INSERT INTO image(category,content,extension) VALUES('dog','efqetgreu6rue4w6tw4','jpg');
INSERT INTO image(category,content,extension) VALUES('cat','q3rqrqrfwrqrftqrq3r','png');
INSERT INTO image(category,content,extension) VALUES('cat','wefrqerqrqrqr','png');
INSERT INTO image(category,content,extension) VALUES('cat','aewrfqwrfqwfqwefqwefwef','gif');