type Props = {
    info: string
}

export default function InfoAtendimento({info}: Props){
    return(
        <h3 className="flex text-lg text-white font-bold">
            {info}
        </h3>
    )
}