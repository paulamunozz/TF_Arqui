import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CalculadoraHuellaCarbonoService {
  private http: HttpClient = inject(HttpClient);
  private apiKey = 'XV3X4CFBXN317A7N5T4JWYTW1W';
  private url = 'https://api.climatiq.io/data/v1/estimate';

  constructor() {
  }

  huellaBus(distancia:number) {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.apiKey}`,
      'Content-Type': 'application/json'
    });

    const body = {
      emission_factor: {
        activity_id: "passenger_vehicle-vehicle_type_bus-fuel_source_diesel-engine_size_na-vehicle_age_na-vehicle_weight_gte_12t",
        source: "MfE",
        region: "NZ",
        year: 2025,
        source_lca_activity: "fuel_combustion",
        data_version: "^28"
      },
      parameters: {
        distance: distancia,
        distance_unit: "km"
      }
    };

    return this.http.post(this.url, body, { headers });
  }

  huellaCarro(distancia:number) {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.apiKey}`,
      'Content-Type': 'application/json'
    });

    const body = {
      emission_factor: {
        activity_id: "passenger_vehicle-vehicle_type_car-fuel_source_na-engine_size_na-vehicle_age_na-vehicle_weight_na",
        source: "EPA",
        region: "US",
        year: 2025,
        source_lca_activity: "use_phase",
        data_version: "^28"
      },
      parameters: {
        distance: distancia,
        distance_unit: "km"
      }
    };

    return this.http.post(this.url, body, { headers })
  }

  huellaVuelo(distancia:number) {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.apiKey}`,
      'Content-Type': 'application/json'
    });

    const body = {
      emission_factor: {
        activity_id: "freight_flight-route_type_domestic-distance_na-weight_na-rf_na-method_na-aircraft_type_na-distance_uplift_na",
        source: "AusLCI",
        region: "AU",
        year: 2022,
        source_lca_activity: "unknown",
        data_version: "^28",
        allowed_data_quality_flags: [
          "notable_methodological_variance"
        ]
      },
      parameters: {
        weight: 23,
        weight_unit: "kg",
        distance: distancia,
        distance_unit: "km"
      }
    };

    return this.http.post(this.url, body, { headers })
  }
}
