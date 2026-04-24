import { useState } from "react";
import { createProject } from "../api/project";
export default function ProjectForm({ toggleForm }){
    const [formData, setFormData] = useState({
        name: "",
        description: "",
        owner: "",
        members: [],
    });

    const handleSubmit = (e) => {
        e.preventDefault();
        createProject(formData);
        toggleForm(false);
    }

    return (
        <>
            <form onSubmit={handleSubmit}>
                <input 
                type="text" 
                placeholder="Enter name..." 
                value={formData.name} 
                onChange={(e) => setFormData({
                    name: e.target.value,
                    description: formData.description,
                    owner: formData.owner,
                    members: formData.members,

                })}
                />
                <input 
                type="text" 
                placeholder="Enter description..."
                value={formData.description}
                onChange={(e) => setFormData({
                    name: formData.name,
                    description: e.target.value,
                    owner: formData.owner,
                    members: formData.members,
                })}
                />
                <input 
                type="text" 
                placeholder="Enter project owner..."
                value={formData.owner}
                onChange={(e) => setFormData({
                    name: formData.name,
                    description: formData.description,
                    owner: e.target.value,
                    members: formData.members,
                })}
                />
                <button type="submit">Submit</button>
            </form>
        </>
    )
}