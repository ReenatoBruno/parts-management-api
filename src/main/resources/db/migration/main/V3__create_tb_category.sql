CREATE TABLE IF NOT EXISTS tb_categoty(

    category_id UUID NOT NULL,
    category_name VARCHAR(60) NOT NULL,
    category_description VARCHAR(255),
    category_active = BOOLEAN NOT NULL,
    category_created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    category_updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    category_created_by VARCHAR(50) NOt NULL,
    category_updated_by VARCHAR(50) NOT NULL,

    CONSTRAINT pk_category PRIMARY KEY (category_id),
    CONSTRAINT uq_category_name UNIQUE (category_name)
);

CREATE INDEX idx_category_name ON tb_category(category_name);