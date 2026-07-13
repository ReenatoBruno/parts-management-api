CREATE TABLE IF NOT EXISTS tb_supplier (
    supplier_id UUID NOT NULL,
    supplier_cnpj VARCHAR(14) NOT NULL,
    supplier_name VARCHAR(150) NOT NULL,
    trade_name VARCHAR(150) NOT NULL,
    supplier_email VARCHAR(150) NOT NULL,
    supplier_phone VARCHAR(15) NOT NULL,
    zip SMALLINT NOT NULL, ,
    street VARCHAR(150) NOT NULL,
    house_number VARCHAR(10) NOT NULL,
    complement VARCHAR(50) NOT NULL,
    district VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL,
    state VARCHAR(2) NOT NULL
    supplier_active BOOLEAN NOT NULL,
    suppleir_created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    supplier_updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    supplier_created_by VARCHAR(255) NOT NULL,
    supplier_updated_by VARCHAR(255) NOT NULL,

    CONSTRAINT pk_supplier PRIMARY KEY (supplier_id),
    CONSTRAINT uk_supplier_cnpj UNIQUE (supplier_cnpj),
    CONSTRAINT uk_trade_name UNIQUE (trade_name)
);

CREATE INDEX idx_supplier_cnpj ON tb_supplier (supplier_cnpj);
CREATE INDEX idx_supplier_trade_name ON tb_supplier (trade_name)