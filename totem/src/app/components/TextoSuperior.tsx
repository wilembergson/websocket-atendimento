type Props = {
    readonly texto: string
}

export default function TextoSuperior({ texto }: Props) {
    return (
        <h1 className='flex bg-slate-700 text-white w-1/2 justify-center items-center rounded-lg font-bold mx-4 mt-4 p-8 text-center text-[2rem] 2xl:text-[3rem]'>
            {texto}
        </h1>
    )
}