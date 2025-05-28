
import InfoAtendimento from "./InfoAtendimento"
import { Atendimento } from "./ListarEmAtendimento"

type Props = {
    readonly atendimento: Atendimento
}

export default function ItemAtendimento({atendimento}:Props){ 

    return(
        <section className="flex w-full justify-between text-white h-1/5 items-center bg-slate-700 my-1 p-1 rounded-sm">
            <InfoAtendimento info={atendimento?.identificacao}/>
            <InfoAtendimento info={atendimento?.tipo}/>
            <InfoAtendimento info={atendimento?.data}/>
        </section>
    )
}