-- Insertar personas naturales
INSERT INTO PERSONA_NATURAL 
(TIPO_IDENTIFICACION, IDENTIFICACION, PRIMER_NOMBRE, SEGUNDO_NOMBRE, PRIMER_APELLIDO, SEGUNDO_APELLIDO, EMAIL, NUMERO_TELEFONICO, FECHA_NACIMIENTO, FECHA_CREACION)
VALUES
-- Persona natural independiente
('CED', '1712345678', 'Juan', 'Carlos', 'Pérez', 'Martínez', 'juan.perez@email.com', '0987654321', '1985-06-15', CURRENT_TIMESTAMP),
-- Representantes legales de las empresas
('CED', '1798765432', 'María', 'Isabel', 'González', 'Rodríguez', 'maria.gonzalez@email.com', '0998765432', '1980-03-20', CURRENT_TIMESTAMP),
('CED', '1723456789', 'Roberto', 'José', 'Sánchez', 'López', 'roberto.sanchez@email.com', '0976543210', '1975-11-10', CURRENT_TIMESTAMP);

-- Insertar personas jurídicas
INSERT INTO PERSONA_JURIDICA 
(PERSONA_NATURAL_ID, RUC, RAZON_SOCIAL, NOMBRE_COMERCIAL, EMAIL, NUMERO_TELEFONICO, FECHA_CONSTITUCION, FECHA_CREACION)
VALUES
(2, '1791234567001', 'TECNOLOGÍA AVANZADA S.A.', 'TECNOAV', 'contacto@tecnoav.com', '0222334455', '2010-01-15', CURRENT_TIMESTAMP),
(3, '1790987654001', 'COMERCIAL DEL VALLE CIA. LTDA.', 'COMVALLE', 'info@comvalle.com', '0233445566', '2015-07-20', CURRENT_TIMESTAMP);

-- Insertar direcciones
INSERT INTO DIRECCION 
(PERSONA_NATURAL_ID, PERSONA_JURIDICA_ID, PROVINCIA, CIUDAD, CANTON, SECTOR, CALLE_PRINCIPAL, CALLE_SECUNDARIA, NUMERO, REFERENCIA, DETALLE)
VALUES
-- Dirección persona natural
(1, NULL, 'Pichincha', 'Quito', 'Quito', 'La Carolina', 'Av. Amazonas', 'Av. Naciones Unidas', 'N45-123', 'Frente al parque', 'Edificio Torre Gris'),
-- Direcciones empresas
(NULL, 1, 'Pichincha', 'Quito', 'Quito', 'Iñaquito', 'Av. República', 'Av. 6 de Diciembre', 'N34-567', 'Junto al centro comercial', 'Edificio Corporativo'),
(NULL, 2, 'Pichincha', 'Quito', 'Quito', 'Cumbayá', 'Av. Interoceánica', 'De los Conquistadores', 'E13-45', 'Cerca del parque central', 'Centro Empresarial');

-- Insertar clientes
INSERT INTO CLIENTE 
(PERSONA_NATURAL_ID, PERSONA_JURIDICA_ID, TIPO, INGRESO_PROMEDIO_MENSUAL, ESTADO, FECHA_CREACION)
VALUES
-- Cliente personal
(1, NULL, 'PER', 2500.00, 'ACT', CURRENT_TIMESTAMP),
-- Clientes corporativos
(NULL, 1, 'COR', 25000.00, 'ACT', CURRENT_TIMESTAMP),
(NULL, 2, 'COR', 18000.00, 'ACT', CURRENT_TIMESTAMP);

-- Crear cuentas bancarias
INSERT INTO CUENTA 
(CLIENTE_ID, TIPO, NUMERO, SALDO_TOTAL, SALDO_DISPONIBLE, SALDO_ACREDITAR, ESTADO, FECHA_CREACION)
VALUES
-- Cuenta de ahorro para cliente personal
(1, 'AHO', '2201234567890123', 3500.00, 3500.00, 0.00, 'ACT', CURRENT_TIMESTAMP),
-- Cuentas corrientes para clientes corporativos
(2, 'COR', '2201987654321098', 45000.00, 45000.00, 0.00, 'ACT', CURRENT_TIMESTAMP),
(3, 'COR', '2201456789012345', 28000.00, 28000.00, 0.00, 'ACT', CURRENT_TIMESTAMP);

-- Crear tarjetas de crédito
INSERT INTO TARJETA 
(CLIENTE_ID, NUMERO, CLAVE, CUPO_APROBADO, CUPO_DISPONIBLE, DIA_CORTE, FECHA_EMISION, ESTADO, FECHA_CREACION)
VALUES
(1, 'encrypted_4532123456789012', 'encrypted_1234', 5000.00, 5000.00, 15, CURRENT_DATE, 'ACT', CURRENT_TIMESTAMP),
(2, 'encrypted_4532987654321098', 'encrypted_5678', 50000.00, 50000.00, 20, CURRENT_DATE, 'ACT', CURRENT_TIMESTAMP),
(3, 'encrypted_4532456789012345', 'encrypted_9012', 35000.00, 35000.00, 25, CURRENT_DATE, 'ACT', CURRENT_TIMESTAMP);

-- Crear transacciones
INSERT INTO TRANSACCION 
(CUENTA_ID, TARJETA_ID, TIPO, CANAL, FECHA_HORA, VALOR, COMISION, TAZA_INTERES, ES_DIFERIDO, ESTADO, FECHA_CREACION)
VALUES
-- Transacción de depósito para cliente personal
(1, NULL, 'DEP', 'WEB', CURRENT_TIMESTAMP, 1000.00, 0.00, 0.00, false, 'APR', CURRENT_TIMESTAMP),
-- Transacción con tarjeta para primer cliente corporativo
(NULL, 2, 'PTC', 'POS', CURRENT_TIMESTAMP, 5000.00, 0.00, 12.00, true, 'APR', CURRENT_TIMESTAMP),
-- Transferencia para segundo cliente corporativo
(3, NULL, 'TRE', 'WEB', CURRENT_TIMESTAMP, 3500.00, 0.50, 0.00, false, 'APR', CURRENT_TIMESTAMP);

-- Crear detalles de transacciones
INSERT INTO DETALLE_TRANSACCION 
(TRANSACCION_ID, TIPO_CUENTA, CUENTA_ORIGEN, CUENTA_DESTINO, BIN_BANCO_ORIGEN, BIN_BANCO_DESTINO, DESCRIPCION)
VALUES
(1, 'AHO', '2201234567890123', NULL, '220', NULL, 'Depósito en cuenta de ahorros'),
(2, NULL, NULL, NULL, '453', NULL, 'Compra en establecimiento comercial'),
(3, 'COR', '2201456789012345', '2201987654321098', '220', '220', 'Transferencia entre cuentas');

-- Crear diferido para la transacción con tarjeta
INSERT INTO DIFERIDO 
(TRANSACCION_ID, CUOTAS, CUOTAS_CANCELADAS, TAZA_INTERES, VALOR_CUOTA, VALOR_DEUDA, VALOR_RESTANTE, VALOR_INTERES, ESTADO)
VALUES
(2, 12, 0, 12.00, 466.67, 5000.00, 5000.00, 600.00, 'PEN');

-- Crear usuarios para cada cliente
INSERT INTO USUARIO 
(CLIENTE_ID, ROL, USUARIO, CONTRASENIA, IMG, ESTADO, FECHA_CREACION)
VALUES
-- Usuario para Juan Pérez (cliente personal)
(1, 'USR', 'jperez', '123', 'default_user.png', 'ACT', CURRENT_TIMESTAMP),
-- Usuario para María González (representante de TECNOLOGÍA AVANZADA S.A.)
(2, 'USR', 'mgonzalez', '123', 'default_user.png', 'ACT', CURRENT_TIMESTAMP),
-- Usuario para Roberto Sánchez (representante de COMERCIAL DEL VALLE CIA. LTDA.)
(3, 'USR', 'rsanchez', '123', 'default_user.png', 'ACT', CURRENT_TIMESTAMP);
