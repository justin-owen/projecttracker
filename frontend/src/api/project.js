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