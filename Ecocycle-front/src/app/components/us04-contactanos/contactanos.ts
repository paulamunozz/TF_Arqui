import { Component } from '@angular/core';

@Component({
  selector: 'app-us04-contactanos',
  imports: [],
  templateUrl: './contactanos.html',
  styleUrl: './contactanos.css',
})
export class Contactanos {
  contacto = {
    whatsapp: '934893433',
    llamadas: '01 2323121',
    correo: 'contactoeco@ecocycle.com',
    direccion: 'Calle ABC 123'
  };
  imagenEquipo = '/Contacto1.png';
  imagenMapa ='/Mapa-Contacto.png';
  constructor() {}
}
