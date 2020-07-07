import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs"
import { map } from "rxjs/operators";
import { Item } from "./item.model";

@Injectable({
    providedIn: "root"
})
export class InventoryService {

    private REST_API_SERVER = "http://localhost:8080/api/inventory"
    
    constructor(private httpService: HttpClient) { }
    
    fetchAllInventoryRecords(): Observable<Item[]> {
        return this.httpService.get(this.REST_API_SERVER).pipe(
            map((res: Item[]) => res));
    }
    
}