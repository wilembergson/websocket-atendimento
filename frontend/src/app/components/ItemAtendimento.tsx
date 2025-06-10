import api from "@/api/api"
import InfoAtendimento from "./InfoAtendimento"
import { Atendimento } from "./ListarAguardando"

type Props = {
    readonly atendimento: Atendimento
}

export default function ItemAtendimento({atendimento}:Props){
    const statusEmAtendimento = "EM_ATENDIMENTO"
    const statusFinalizado = "FINALIZADO"

    async function alterarStatus(status: string){
        try{
            await api.alterarStatus(atendimento.idFicha, {status, idLocal: atendimento.idLocal})
        } catch(e: any){
            console.log(e)
        }
    }

    return(
        <section className="flex w-full justify-between items-center bg-slate-700 my-1 p-1 rounded-sm">
            <InfoAtendimento info={atendimento.identificacaoFicha}/>
            <InfoAtendimento info={atendimento.tipoFicha}/>
            <InfoAtendimento info={atendimento.data}/>
            <InfoAtendimento info={atendimento.nomeLocal}/>
            {atendimento.status === "AGUARDANDO" ?
            <button className="flex bg-blue-600 text-lg text-white font-bold p-1 rounded-sm cursor-pointer" onClick={() => alterarStatus(statusEmAtendimento)}>
                Atender
            </button>
            :
            <>
            <button className="flex bg-orange-600 p-1 rounded-sm text-lg text-white font-bold cursor-pointer">
                Chamar
            </button>
            <button className="flex bg-green-600 p-1 rounded-sm text-lg text-white font-bold cursor-pointer" onClick={() => alterarStatus(statusFinalizado)}>
                Finalizar
            </button>
            </>
            }
        </section>
    )
}