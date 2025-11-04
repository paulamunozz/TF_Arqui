import {Component, inject} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {MatCard, MatCardContent} from '@angular/material/card';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';

@Component({
  selector: 'app-us37-municipalidad-modificacion-contrasena',
  imports: [
    MatCard,
    MatCardContent,
    ReactiveFormsModule,
    MatFormField,
    MatLabel,
    MatInput
  ],
  templateUrl: './municipalidad-modificacion-contrasena.html',
  styleUrl: './municipalidad-modificacion-contrasena.css',
})
export class MunicipalidadModificacionContrasena {
  formConstrasena: FormGroup;
  private fb: FormBuilder = inject(FormBuilder);
  private router: Router = inject(Router);

  constructor() {
    this.formConstrasena = this.fb.group({
      contrasena: ['', Validators.required]
    })
  }

  onSubmit(){
  }
}
