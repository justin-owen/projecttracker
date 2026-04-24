import axios from "axios";
import { api } from "./client"
export async function getProjects() {
    try{
        const { data } = await api.get("/api/projects/");
        return data;
    } catch(err){
        return {
            ok: false,
            errMessage: err.message
        };
    }
}
export async function createProject(project){
    try {
        console.log(project);
        const { data } = await api.post('/api/projects/create', {
            name: project.name,
            description: project.description,
            owner: project.owner,
            members: [],
        });
        return data;
    } catch (err) {
        console.error(err);
    }
}