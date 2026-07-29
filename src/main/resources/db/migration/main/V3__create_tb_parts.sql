CREATE TABLE IF NOT EXISTS tb_parts(

    part_id UUID NOT NULL,
    part_number VARCHAR(50) NOT NULL,
    part_name VARCHAR(100) NOT NULL,
    price NUMERIC (10, 2) NOT NULL,
    quantity INTEGER NOT NULL,
    part_active BOOLEAN NOT NULL,
    part_description VARCHAR(255),
    supplier_id UUID NOT NULL,
    part_created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    part_updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    part_created_by VARCHAR(255) NOt NULL,
    part_updated_by VARCHAR(255) NOT NULL,

    CONSTRAINT pk_part_id PRIMARY KEY (part_id),

    CONSTRAINT uq_part_number UNIQUE (part_number),

    CONSTRAINT chk_part_number_upper CHECK (part_number = UPPER(part_number)),
    CONSTRAINT chk_price_positive CHECK (price > 0),
    CONSTRAINT chk_quantity_positive CHECK (quantity > 0),

    CONSTRAINT fk_parts_supplier FOREIGN KEY (supplier_id) REFERENCES tb_supplier (supplier_id)

);

CREATE INDEX idx_part_number on tb_parts (part_number);