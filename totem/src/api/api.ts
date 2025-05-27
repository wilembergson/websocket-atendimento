import axios from "axios"

export const API_URL = "http://localhost:8080"

type AlterarStatus = {
    status: string
}

async function listar(){
    return await axios.get(`${API_URL}/atendimento/listar-painel`)
}

async function alterarStatus(idFicha: number, data: AlterarStatus){
    return await axios.post(`${API_URL}/atendimento/alterar/${idFicha}`, data)
}

const api = {
    listar,
    alterarStatus
}

export default api