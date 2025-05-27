type Props = {
    info: string
}

export default function InfoAtendimento({info}: Props){
    return(
        <h3 className="flex text-4xl font-bold">
            {info}
        </h3>
    )
}