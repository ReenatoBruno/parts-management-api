CREATE TABLE IF NOT EXISTS tb_order (

    order_id UUID NOT NULL,
    order_number BIGINT NOT NULL,
    dealership_id UUID NOT NULL,
    order_status VARCHAR(25) NOT NULL,
    total_amount NUMERIC(10, 2) NOT NULL,
    order_active BOOLEAN NOT NULL,
    order_created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    order_updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    order_created_by VARCHAR(255) NOT NULL,
    order_updated_by VARCHAR(255) NOT NULL,

    CONSTRAINT pk_tb_order PRIMARY KEY (order_id),

    CONSTRAINT uq_tb_order_number UNIQUE (order_number),

    CONSTRAINT fk_order_dealership FOREIGN KEY (dealership_id) REFERENCES tb_dealership (dealer_id),

    CONSTRAINT chk_order_total_amount CHECK (total_amount >= 0)
);

CREATE INDEX idx_tb_order_dealership_id ON tb_order (dealership_id);
CREATE INDEX idx_tb_order_status ON tb_order (order_status);
CREATE INDEX idx_tb_order_created_at ON tb_order (order_created_at DESC);
