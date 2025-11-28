import {Component, inject} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {VecinoService} from '../../services/vecino-service';
import {Vecino} from '../../model/vecino';

@Component({
  selector: 'app-menu',
  imports: [
    RouterLink
  ],
  templateUrl: './menu.html',
  styleUrl: './menu.css',
})
export class Menu {
  private rol:any;
  icono = 0;

  fotos = [
    '/icono-default.png',
    '/icono-1.png',
    '/icono-2.png',
    '/icono-3.png'
  ];

  constructor(private vecinoService: VecinoService) {
    this.vecinoService.icono$.subscribe(i => this.icono = i);
  }

  esVecino(): boolean{
    let siEs = false;
    this.rol = localStorage.getItem("rol");
    if(this.rol == "ROLE_VECINO"){
      siEs = true;
    }
    return siEs;
  }

  esMunicipalidad(): boolean{
    let siEs = false;
    this.rol = localStorage.getItem("rol");
    if(this.rol == "ROLE_MUNICIPALIDAD"){
      siEs = true;
    }
    return siEs;
  }
}
