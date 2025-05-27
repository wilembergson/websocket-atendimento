import axios from "axios"

export const API_URL = "http://localhost:8080"

type AlterarStatus = {
    status: string
}

async function listar(status: string){
    return await axios.get(`${API_URL}/atendimento/listar/${status}`)
}

async function alterarStatus(idFicha: number, data: AlterarStatus){
    return await axios.post(`${API_URL}/atendimento/alterar/${idFicha}`, data)
}

const api = {
    listar,
    alterarStatus
}

export default api