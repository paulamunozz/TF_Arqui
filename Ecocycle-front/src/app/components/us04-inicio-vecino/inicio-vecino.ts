import { Component, OnInit } from '@angular/core';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-us04-inicio-vecino',
  templateUrl: './inicio-vecino.html',
  styleUrl: './inicio-vecino.css',
  imports: [
    RouterLink
  ]
})
export class InicioComponent implements OnInit {

  tituloAcerca: string = 'Acerca de nosotros';

  constructor() { }

  ngOnInit(): void {
    console.log('Componente de Inicio (Acerca de Nosotros) cargado.');
  }
}
