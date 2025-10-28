import { Component } from '@angular/core';
import {ActivatedRoute, RouterLink} from '@angular/router';

@Component({
  selector: 'app-us21-us22-us23-municipalidad-detalle-evento',
  imports: [
    RouterLink
  ],
  templateUrl: './municipalidad-detalle-evento.html',
  styleUrl: './municipalidad-detalle-evento.css',
})
export class MunicipalidadDetalleEvento {
  id:number | undefined;
  constructor(private route:ActivatedRoute) { }
  ngOnInit(){
    this.route.params.subscribe(params => {
      this.id = +params['id'];
    })
  }

  popUpVisible = false;
  mostrarPopUp()
  {
    this.popUpVisible = true;
  }
  cerrarPopUp(){
    this.popUpVisible = false;
  }

evento =
  {id:1, nombre:'Octuble sin papel!!', descripcion:'Nuestra meta con este desafío es lograr para el fin de mes de septiembre recolectar como mínimo 500 kg de todo tipo de plásticos para poder transformarlos en nuevos materiales útiles como bolsas o botellas y reducir la cantidad de desechos que acaban contaminando los mares y las calles de nuestro país.\n' +
      'Asegúrese al momento de reciclar los plásticos que no tengan ningún tipo de desecho orgánico ya que podría causar generación de bacterias y ser riesgoso para nuestro personal que trata con el reciclaje.', tipo:'Papel', metodo:'En casa', fechaInicio:'26/10/2025', fechaFin:'26/10/2025', pesoObjetivo:'500',pesoActual:'125', bonificacion:'1.5'}

comentarios=[
  {id:'1', nombre:'Paula Muñoz', comentario:'Participé activamente en la campaña de reciclaje de vidrio.'},
  {id:'2', nombre:'Mae Villachica', comentario:'Fue una buena experiencia para aprender más sobre el reciclaje.'},
  {id:'3', nombre:'José', comentario:'Me gustó ayudar en la jornada de reciclaje del barrio.'}]

}
