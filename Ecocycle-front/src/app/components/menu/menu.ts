import {Component, inject} from '@angular/core';
import {Router, RouterLink} from '@angular/router';

@Component({
  selector: 'app-menu',
  imports: [
    RouterLink
  ],
  templateUrl: './menu.html',
  styleUrl: './menu.css',
})
export class Menu {
  private router : Router = inject(Router);
  private rol:any;

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
