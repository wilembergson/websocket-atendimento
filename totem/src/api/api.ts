import axios from "axios"

export const API_URL = "http://localhost:8080"

type AlterarStatus = {
    status: string
}

async function listar(idPainel: number){
    return await axios.get(`${API_URL}/atendimento/listar-painel?idLocal=${idPainel}`)
}

async function alterarStatus(idFicha: number, data: AlterarStatus){
    return await axios.post(`${API_URL}/atendimento/alterar/${idFicha}`, data)
}

const api = {
    listar,
    alterarStatus
}

export default api