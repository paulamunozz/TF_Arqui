INSERT INTO public.municipalidad 
(codigo, contrasena, distrito, puntajetotal, puesto)
VALUES
('SU002025', '123', 'Surco', 12, 2),
('VS002025', '124', 'Villa el Salvador', 20, 1),
('MJ002025', '125', 'Miraflores', 15, 3);

INSERT INTO public.vecino 
(dni, contrasena, nombre, genero, edad, distrito, direccion, puntajetotal, icono, puesto, eliminado)
VALUES
('12345679', '1234', 'Camila Gomez', 'F', 21, 'Surco', 'Mzn 458', 7, 0, 2, false),
('12345670', '123', 'Paula', 'F', 19, 'San Borja', 'Mzn 123', 0, 0, 3, false),
('12345677', '1234', 'Ana Torres', 'F', 21, 'Surco', 'Mzn 458', 20, 0, 1, true);


INSERT INTO public.reciclaje (id_vecino, peso, tipo, metodo, fecha, puntaje)
VALUES
(1, 50.00, 'Plástico', 'Camión de reciclaje', '2025-09-28', 7),
(2, 50.00, 'Plástico', 'Camión de reciclaje', '2025-09-28', 5),
(2, 50.00, 'Plástico', 'Camión de reciclaje', '2025-09-28', 5),
(2, 50.00, 'Plástico', 'Camión de reciclaje', '2025-09-28', 5),
(2, 50.00, 'Plástico', 'Camión de reciclaje', '2025-09-28', 5);


INSERT INTO public.evento (
    id_municipalidad, nombre, descripcion, peso_objetivo, peso_actual,
    fecha_inicio, fecha_fin, tipo, metodo, bonificacion, situacion
)
VALUES (
    1, 'Mes sin plástico 2025', 'Recicle todo el plástico que pueda durante este mes',
    300.00, 50.00, '2025-09-10', '2025-09-30', 'Plástico', 'Camión de reciclaje', 1.5, false
);


INSERT INTO public.evento_x_vecino (id_evento, id_vecino, comentario)
VALUES (1, 1, NULL);
